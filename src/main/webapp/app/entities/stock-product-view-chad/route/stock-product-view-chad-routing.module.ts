import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StockProductViewChadComponent } from '../list/stock-product-view-chad.component';
import { StockProductViewChadDetailComponent } from '../detail/stock-product-view-chad-detail.component';
import { StockProductViewChadUpdateComponent } from '../update/stock-product-view-chad-update.component';
import { StockProductViewChadRoutingResolveService } from './stock-product-view-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const stockProductViewRoute: Routes = [
  {
    path: '',
    component: StockProductViewChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StockProductViewChadDetailComponent,
    resolve: {
      stockProductView: StockProductViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StockProductViewChadUpdateComponent,
    resolve: {
      stockProductView: StockProductViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StockProductViewChadUpdateComponent,
    resolve: {
      stockProductView: StockProductViewChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stockProductViewRoute)],
  exports: [RouterModule],
})
export class StockProductViewChadRoutingModule {}
