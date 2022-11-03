import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductTransactionChadComponent } from '../list/product-transaction-chad.component';
import { ProductTransactionChadDetailComponent } from '../detail/product-transaction-chad-detail.component';
import { ProductTransactionChadUpdateComponent } from '../update/product-transaction-chad-update.component';
import { ProductTransactionChadRoutingResolveService } from './product-transaction-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productTransactionRoute: Routes = [
  {
    path: '',
    component: ProductTransactionChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductTransactionChadDetailComponent,
    resolve: {
      productTransaction: ProductTransactionChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductTransactionChadUpdateComponent,
    resolve: {
      productTransaction: ProductTransactionChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductTransactionChadUpdateComponent,
    resolve: {
      productTransaction: ProductTransactionChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productTransactionRoute)],
  exports: [RouterModule],
})
export class ProductTransactionChadRoutingModule {}
