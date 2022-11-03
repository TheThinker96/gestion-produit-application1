import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StockProductChadComponent } from '../list/stock-product-chad.component';
import { StockProductChadDetailComponent } from '../detail/stock-product-chad-detail.component';
import { StockProductChadUpdateComponent } from '../update/stock-product-chad-update.component';
import { StockProductChadRoutingResolveService } from './stock-product-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const stockProductRoute: Routes = [
  {
    path: '',
    component: StockProductChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StockProductChadDetailComponent,
    resolve: {
      stockProduct: StockProductChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StockProductChadUpdateComponent,
    resolve: {
      stockProduct: StockProductChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StockProductChadUpdateComponent,
    resolve: {
      stockProduct: StockProductChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stockProductRoute)],
  exports: [RouterModule],
})
export class StockProductChadRoutingModule {}
