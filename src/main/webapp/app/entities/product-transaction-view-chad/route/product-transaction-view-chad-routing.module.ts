import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductTransactionViewChadComponent } from '../list/product-transaction-view-chad.component';
import { ProductTransactionViewChadDetailComponent } from '../detail/product-transaction-view-chad-detail.component';
import { ProductTransactionViewChadUpdateComponent } from '../update/product-transaction-view-chad-update.component';
import { ProductTransactionViewChadRoutingResolveService } from './product-transaction-view-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productTransactionViewRoute: Routes = [
  {
    path: '',
    component: ProductTransactionViewChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductTransactionViewChadDetailComponent,
    resolve: {
      productTransactionView: ProductTransactionViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductTransactionViewChadUpdateComponent,
    resolve: {
      productTransactionView: ProductTransactionViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductTransactionViewChadUpdateComponent,
    resolve: {
      productTransactionView: ProductTransactionViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productTransactionViewRoute)],
  exports: [RouterModule],
})
export class ProductTransactionViewChadRoutingModule {}
