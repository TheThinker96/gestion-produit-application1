import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductTransactionChad } from '../product-transaction-chad.model';
import { ProductTransactionChadService } from '../service/product-transaction-chad.service';

@Injectable({ providedIn: 'root' })
export class ProductTransactionChadRoutingResolveService implements Resolve<IProductTransactionChad | null> {
  constructor(protected service: ProductTransactionChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductTransactionChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productTransaction: HttpResponse<IProductTransactionChad>) => {
          if (productTransaction.body) {
            return of(productTransaction.body);
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
