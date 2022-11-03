import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserCashBackChadComponent } from '../list/user-cash-back-chad.component';
import { UserCashBackChadDetailComponent } from '../detail/user-cash-back-chad-detail.component';
import { UserCashBackChadUpdateComponent } from '../update/user-cash-back-chad-update.component';
import { UserCashBackChadRoutingResolveService } from './user-cash-back-chad-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userCashBackRoute: Routes = [
  {
    path: '',
    component: UserCashBackChadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserCashBackChadDetailComponent,
    resolve: {
      userCashBack: UserCashBackChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserCashBackChadUpdateComponent,
    resolve: {
      userCashBack: UserCashBackChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserCashBackChadUpdateComponent,
    resolve: {
      userCashBack: UserCashBackChadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userCashBackRoute)],
  exports: [RouterModule],
})
export class UserCashBackChadRoutingModule {}
