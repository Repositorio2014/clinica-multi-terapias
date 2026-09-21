import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProntuario, NewProntuario } from '../prontuario.model';

export type PartialUpdateProntuario = Partial<IProntuario> & Pick<IProntuario, 'id'>;

type RestOf<T extends IProntuario | NewProntuario> = Omit<T, 'dataAtendimento'> & {
  dataAtendimento?: string | null;
};

export type RestProntuario = RestOf<IProntuario>;

export type NewRestProntuario = RestOf<NewProntuario>;

export type PartialUpdateRestProntuario = RestOf<PartialUpdateProntuario>;

export type EntityResponseType = HttpResponse<IProntuario>;
export type EntityArrayResponseType = HttpResponse<IProntuario[]>;

@Injectable({ providedIn: 'root' })
export class ProntuarioService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prontuarios');

  create(prontuario: NewProntuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prontuario);
    return this.http
      .post<RestProntuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prontuario: IProntuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prontuario);
    return this.http
      .put<RestProntuario>(`${this.resourceUrl}/${this.getProntuarioIdentifier(prontuario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(prontuario: PartialUpdateProntuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prontuario);
    return this.http
      .patch<RestProntuario>(`${this.resourceUrl}/${this.getProntuarioIdentifier(prontuario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProntuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProntuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProntuarioIdentifier(prontuario: Pick<IProntuario, 'id'>): number {
    return prontuario.id;
  }

  compareProntuario(o1: Pick<IProntuario, 'id'> | null, o2: Pick<IProntuario, 'id'> | null): boolean {
    return o1 && o2 ? this.getProntuarioIdentifier(o1) === this.getProntuarioIdentifier(o2) : o1 === o2;
  }

  addProntuarioToCollectionIfMissing<Type extends Pick<IProntuario, 'id'>>(
    prontuarioCollection: Type[],
    ...prontuariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prontuarios: Type[] = prontuariosToCheck.filter(isPresent);
    if (prontuarios.length > 0) {
      const prontuarioCollectionIdentifiers = prontuarioCollection.map(prontuarioItem => this.getProntuarioIdentifier(prontuarioItem));
      const prontuariosToAdd = prontuarios.filter(prontuarioItem => {
        const prontuarioIdentifier = this.getProntuarioIdentifier(prontuarioItem);
        if (prontuarioCollectionIdentifiers.includes(prontuarioIdentifier)) {
          return false;
        }
        prontuarioCollectionIdentifiers.push(prontuarioIdentifier);
        return true;
      });
      return [...prontuariosToAdd, ...prontuarioCollection];
    }
    return prontuarioCollection;
  }

  protected convertDateFromClient<T extends IProntuario | NewProntuario | PartialUpdateProntuario>(prontuario: T): RestOf<T> {
    return {
      ...prontuario,
      dataAtendimento: prontuario.dataAtendimento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProntuario: RestProntuario): IProntuario {
    return {
      ...restProntuario,
      dataAtendimento: restProntuario.dataAtendimento ? dayjs(restProntuario.dataAtendimento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProntuario>): HttpResponse<IProntuario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProntuario[]>): HttpResponse<IProntuario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
