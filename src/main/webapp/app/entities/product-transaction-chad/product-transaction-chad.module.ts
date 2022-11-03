import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductTransactionChadComponent } from './list/product-transaction-chad.component';
import { ProductTransactionChadDetailComponent } from './detail/product-transaction-chad-detail.component';
import { ProductTransactionChadUpdateComponent } from './update/product-transaction-chad-update.component';
import { ProductTransactionChadDeleteDialogComponent } from './delete/product-transaction-chad-delete-dialog.component';
import { ProductTransactionChadRoutingModule } from './route/product-transaction-chad-routing.module';

@NgModule({
  imports: [SharedModule, ProductTransactionChadRoutingModule],
  declarations: [
    ProductTransactionChadComponent,
    ProductTransactionChadDetailComponent,
    ProductTransactionChadUpdateComponent,
    ProductTransactionChadDeleteDialogComponent,
  ],
})
export class ProductTransactionChadModule {}
