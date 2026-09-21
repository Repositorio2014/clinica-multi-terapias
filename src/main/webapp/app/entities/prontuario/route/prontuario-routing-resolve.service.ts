import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProntuario } from '../prontuario.model';
import { ProntuarioService } from '../service/prontuario.service';

const prontuarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IProntuario> => {
  const id = route.params.id;
  if (id) {
    return inject(ProntuarioService)
      .find(id)
      .pipe(
        mergeMap((prontuario: HttpResponse<IProntuario>) => {
          if (prontuario.body) {
            return of(prontuario.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default prontuarioResolve;
