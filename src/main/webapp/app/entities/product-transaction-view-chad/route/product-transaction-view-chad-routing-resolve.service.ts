import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductTransactionViewChad } from '../product-transaction-view-chad.model';
import { ProductTransactionViewChadService } from '../service/product-transaction-view-chad.service';

@Injectable({ providedIn: 'root' })
export class ProductTransactionViewChadRoutingResolveService implements Resolve<IProductTransactionViewChad | null> {
  constructor(protected service: ProductTransactionViewChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductTransactionViewChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productTransactionView: HttpResponse<IProductTransactionViewChad>) => {
          if (productTransactionView.body) {
            return of(productTransactionView.body);
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
