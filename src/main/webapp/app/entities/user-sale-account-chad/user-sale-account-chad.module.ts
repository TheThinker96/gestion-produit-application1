import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserSaleAccountChadComponent } from './list/user-sale-account-chad.component';
import { UserSaleAccountChadDetailComponent } from './detail/user-sale-account-chad-detail.component';
import { UserSaleAccountChadUpdateComponent } from './update/user-sale-account-chad-update.component';
import { UserSaleAccountChadDeleteDialogComponent } from './delete/user-sale-account-chad-delete-dialog.component';
import { UserSaleAccountChadRoutingModule } from './route/user-sale-account-chad-routing.module';

@NgModule({
  imports: [SharedModule, UserSaleAccountChadRoutingModule],
  declarations: [
    UserSaleAccountChadComponent,
    UserSaleAccountChadDetailComponent,
    UserSaleAccountChadUpdateComponent,
    UserSaleAccountChadDeleteDialogComponent,
  ],
})
export class UserSaleAccountChadModule {}
