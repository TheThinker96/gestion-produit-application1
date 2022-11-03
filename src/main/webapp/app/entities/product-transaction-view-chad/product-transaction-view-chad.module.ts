import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductTransactionViewChadComponent } from './list/product-transaction-view-chad.component';
import { ProductTransactionViewChadDetailComponent } from './detail/product-transaction-view-chad-detail.component';
import { ProductTransactionViewChadUpdateComponent } from './update/product-transaction-view-chad-update.component';
import { ProductTransactionViewChadDeleteDialogComponent } from './delete/product-transaction-view-chad-delete-dialog.component';
import { ProductTransactionViewChadRoutingModule } from './route/product-transaction-view-chad-routing.module';

@NgModule({
  imports: [SharedModule, ProductTransactionViewChadRoutingModule],
  declarations: [
    ProductTransactionViewChadComponent,
    ProductTransactionViewChadDetailComponent,
    ProductTransactionViewChadUpdateComponent,
    ProductTransactionViewChadDeleteDialogComponent,
  ],
})
export class ProductTransactionViewChadModule {}
