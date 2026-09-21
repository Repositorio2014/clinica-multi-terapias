import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfissional, NewProfissional } from '../profissional.model';

export type PartialUpdateProfissional = Partial<IProfissional> & Pick<IProfissional, 'id'>;

export type EntityResponseType = HttpResponse<IProfissional>;
export type EntityArrayResponseType = HttpResponse<IProfissional[]>;

@Injectable({ providedIn: 'root' })
export class ProfissionalService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/profissionals');

  create(profissional: NewProfissional): Observable<EntityResponseType> {
    return this.http.post<IProfissional>(this.resourceUrl, profissional, { observe: 'response' });
  }

  update(profissional: IProfissional): Observable<EntityResponseType> {
    return this.http.put<IProfissional>(`${this.resourceUrl}/${this.getProfissionalIdentifier(profissional)}`, profissional, {
      observe: 'response',
    });
  }

  partialUpdate(profissional: PartialUpdateProfissional): Observable<EntityResponseType> {
    return this.http.patch<IProfissional>(`${this.resourceUrl}/${this.getProfissionalIdentifier(profissional)}`, profissional, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfissional>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfissional[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfissionalIdentifier(profissional: Pick<IProfissional, 'id'>): number {
    return profissional.id;
  }

  compareProfissional(o1: Pick<IProfissional, 'id'> | null, o2: Pick<IProfissional, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfissionalIdentifier(o1) === this.getProfissionalIdentifier(o2) : o1 === o2;
  }

  addProfissionalToCollectionIfMissing<Type extends Pick<IProfissional, 'id'>>(
    profissionalCollection: Type[],
    ...profissionalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const profissionals: Type[] = profissionalsToCheck.filter(isPresent);
    if (profissionals.length > 0) {
      const profissionalCollectionIdentifiers = profissionalCollection.map(profissionalItem =>
        this.getProfissionalIdentifier(profissionalItem),
      );
      const profissionalsToAdd = profissionals.filter(profissionalItem => {
        const profissionalIdentifier = this.getProfissionalIdentifier(profissionalItem);
        if (profissionalCollectionIdentifiers.includes(profissionalIdentifier)) {
          return false;
        }
        profissionalCollectionIdentifiers.push(profissionalIdentifier);
        return true;
      });
      return [...profissionalsToAdd, ...profissionalCollection];
    }
    return profissionalCollection;
  }
}
