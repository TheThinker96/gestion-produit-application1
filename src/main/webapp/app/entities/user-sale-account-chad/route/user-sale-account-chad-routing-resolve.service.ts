import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserSaleAccountChad } from '../user-sale-account-chad.model';
import { UserSaleAccountChadService } from '../service/user-sale-account-chad.service';

@Injectable({ providedIn: 'root' })
export class UserSaleAccountChadRoutingResolveService implements Resolve<IUserSaleAccountChad | null> {
  constructor(protected service: UserSaleAccountChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserSaleAccountChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userSaleAccount: HttpResponse<IUserSaleAccountChad>) => {
          if (userSaleAccount.body) {
            return of(userSaleAccount.body);
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
