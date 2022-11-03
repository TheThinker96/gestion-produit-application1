import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductSaleViewChadComponent } from '../list/product-sale-view-chad.component';
import { ProductSaleViewChadDetailComponent } from '../detail/product-sale-view-chad-detail.component';
import { ProductSaleViewChadUpdateComponent } from '../update/product-sale-view-chad-update.component';
import { ProductSaleViewChadRoutingResolveService } from './product-sale-view-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productSaleViewRoute: Routes = [
  {
    path: '',
    component: ProductSaleViewChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductSaleViewChadDetailComponent,
    resolve: {
      productSaleView: ProductSaleViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductSaleViewChadUpdateComponent,
    resolve: {
      productSaleView: ProductSaleViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductSaleViewChadUpdateComponent,
    resolve: {
      productSaleView: ProductSaleViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productSaleViewRoute)],
  exports: [RouterModule],
})
export class ProductSaleViewChadRoutingModule {}
