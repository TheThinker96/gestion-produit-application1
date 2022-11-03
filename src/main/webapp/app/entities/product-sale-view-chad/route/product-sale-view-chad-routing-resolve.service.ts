import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductSaleViewChad } from '../product-sale-view-chad.model';
import { ProductSaleViewChadService } from '../service/product-sale-view-chad.service';

@Injectable({ providedIn: 'root' })
export class ProductSaleViewChadRoutingResolveService implements Resolve<IProductSaleViewChad | null> {
  constructor(protected service: ProductSaleViewChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductSaleViewChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productSaleView: HttpResponse<IProductSaleViewChad>) => {
          if (productSaleView.body) {
            return of(productSaleView.body);
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
