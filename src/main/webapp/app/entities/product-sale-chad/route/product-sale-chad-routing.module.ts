import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductSaleChadComponent } from '../list/product-sale-chad.component';
import { ProductSaleChadDetailComponent } from '../detail/product-sale-chad-detail.component';
import { ProductSaleChadUpdateComponent } from '../update/product-sale-chad-update.component';
import { ProductSaleChadRoutingResolveService } from './product-sale-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productSaleRoute: Routes = [
  {
    path: '',
    component: ProductSaleChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductSaleChadDetailComponent,
    resolve: {
      productSale: ProductSaleChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductSaleChadUpdateComponent,
    resolve: {
      productSale: ProductSaleChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductSaleChadUpdateComponent,
    resolve: {
      productSale: ProductSaleChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productSaleRoute)],
  exports: [RouterModule],
})
export class ProductSaleChadRoutingModule {}
