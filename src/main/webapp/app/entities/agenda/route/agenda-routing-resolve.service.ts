import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgenda } from '../agenda.model';
import { AgendaService } from '../service/agenda.service';

const agendaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgenda> => {
  const id = route.params.id;
  if (id) {
    return inject(AgendaService)
      .find(id)
      .pipe(
        mergeMap((agenda: HttpResponse<IAgenda>) => {
          if (agenda.body) {
            return of(agenda.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default agendaResolve;
