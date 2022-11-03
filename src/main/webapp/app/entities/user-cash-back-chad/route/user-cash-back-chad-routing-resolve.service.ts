import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserCashBackChad } from '../user-cash-back-chad.model';
import { UserCashBackChadService } from '../service/user-cash-back-chad.service';

@Injectable({ providedIn: 'root' })
export class UserCashBackChadRoutingResolveService implements Resolve<IUserCashBackChad | null> {
  constructor(protected service: UserCashBackChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserCashBackChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userCashBack: HttpResponse<IUserCashBackChad>) => {
          if (userCashBack.body) {
            return of(userCashBack.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
