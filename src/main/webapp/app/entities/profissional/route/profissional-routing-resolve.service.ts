import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProfissional } from '../profissional.model';
import { ProfissionalService } from '../service/profissional.service';

const profissionalResolve = (route: ActivatedRouteSnapshot): Observable<null | IProfissional> => {
  const id = route.params.id;
  if (id) {
    return inject(ProfissionalService)
      .find(id)
      .pipe(
        mergeMap((profissional: HttpResponse<IProfissional>) => {
          if (profissional.body) {
            return of(profissional.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default profissionalResolve;
