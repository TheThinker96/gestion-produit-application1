import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserSaleAccountChadComponent } from '../list/user-sale-account-chad.component';
import { UserSaleAccountChadDetailComponent } from '../detail/user-sale-account-chad-detail.component';
import { UserSaleAccountChadUpdateComponent } from '../update/user-sale-account-chad-update.component';
import { UserSaleAccountChadRoutingResolveService } from './user-sale-account-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userSaleAccountRoute: Routes = [
  {
    path: '',
    component: UserSaleAccountChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserSaleAccountChadDetailComponent,
    resolve: {
      userSaleAccount: UserSaleAccountChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserSaleAccountChadUpdateComponent,
    resolve: {
      userSaleAccount: UserSaleAccountChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserSaleAccountChadUpdateComponent,
    resolve: {
      userSaleAccount: UserSaleAccountChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userSaleAccountRoute)],
  exports: [RouterModule],
})
export class UserSaleAccountChadRoutingModule {}
