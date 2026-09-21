import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISala } from '../sala.model';
import { SalaService } from '../service/sala.service';

const salaResolve = (route: ActivatedRouteSnapshot): Observable<null | ISala> => {
  const id = route.params.id;
  if (id) {
    return inject(SalaService)
      .find(id)
      .pipe(
        mergeMap((sala: HttpResponse<ISala>) => {
          if (sala.body) {
            return of(sala.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salaResolve;
