import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductChadComponent } from '../list/product-chad.component';
import { ProductChadDetailComponent } from '../detail/product-chad-detail.component';
import { ProductChadUpdateComponent } from '../update/product-chad-update.component';
import { ProductChadRoutingResolveService } from './product-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productRoute: Routes = [
  {
    path: '',
    component: ProductChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductChadDetailComponent,
    resolve: {
      product: ProductChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductChadUpdateComponent,
    resolve: {
      product: ProductChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductChadUpdateComponent,
    resolve: {
      product: ProductChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productRoute)],
  exports: [RouterModule],
})
export class ProductChadRoutingModule {}
