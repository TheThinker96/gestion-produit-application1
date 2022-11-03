import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductChad } from '../product-chad.model';
import { ProductChadService } from '../service/product-chad.service';

@Injectable({ providedIn: 'root' })
export class ProductChadRoutingResolveService implements Resolve<IProductChad | null> {
  constructor(protected service: ProductChadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductChad | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((product: HttpResponse<IProductChad>) => {
          if (product.body) {
            return of(product.body);
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
