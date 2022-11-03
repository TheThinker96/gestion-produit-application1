import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStockProductViewChad } from '../stock-product-view-chad.model';
import { StockProductViewChadService } from '../service/stock-product-view-chad.service';

@Injectable({ providedIn: 'root' })
export class StockProductViewChadRoutingResolveService implements Resolve<IStockProductViewChad | null> {
  constructor(protected service: StockProductViewChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockProductViewChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stockProductView: HttpResponse<IStockProductViewChad>) => {
          if (stockProductView.body) {
            return of(stockProductView.body);
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
