import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductSaleChad } from '../product-sale-chad.model';
import { ProductSaleChadService } from '../service/product-sale-chad.service';

@Injectable({ providedIn: 'root' })
export class ProductSaleChadRoutingResolveService implements Resolve<IProductSaleChad | null> {
  constructor(protected service: ProductSaleChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductSaleChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productSale: HttpResponse<IProductSaleChad>) => {
          if (productSale.body) {
            return of(productSale.body);
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
