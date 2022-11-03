import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserCashBackChadComponent } from './list/user-cash-back-chad.component';
import { UserCashBackChadDetailComponent } from './detail/user-cash-back-chad-detail.component';
import { UserCashBackChadUpdateComponent } from './update/user-cash-back-chad-update.component';
import { UserCashBackChadDeleteDialogComponent } from './delete/user-cash-back-chad-delete-dialog.component';
import { UserCashBackChadRoutingModule } from './route/user-cash-back-chad-routing.module';

@NgModule({
  imports: [SharedModule, UserCashBackChadRoutingModule],
  declarations: [
    UserCashBackChadComponent,
    UserCashBackChadDetailComponent,
    UserCashBackChadUpdateComponent,
    UserCashBackChadDeleteDialogComponent,
  ],
})
export class UserCashBackChadModule {}
