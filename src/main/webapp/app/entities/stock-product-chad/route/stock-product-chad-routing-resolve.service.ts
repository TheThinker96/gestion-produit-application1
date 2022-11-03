import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStockProductChad } from '../stock-product-chad.model';
import { StockProductChadService } from '../service/stock-product-chad.service';

@Injectable({ providedIn: 'root' })
export class StockProductChadRoutingResolveService implements Resolve<IStockProductChad | null> {
  constructor(protected service: StockProductChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockProductChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stockProduct: HttpResponse<IStockProductChad>) => {
          if (stockProduct.body) {
            return of(stockProduct.body);
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
