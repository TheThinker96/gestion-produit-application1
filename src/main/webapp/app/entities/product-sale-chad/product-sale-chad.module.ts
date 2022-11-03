import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductSaleChadComponent } from './list/product-sale-chad.component';
import { ProductSaleChadDetailComponent } from './detail/product-sale-chad-detail.component';
import { ProductSaleChadUpdateComponent } from './update/product-sale-chad-update.component';
import { ProductSaleChadDeleteDialogComponent } from './delete/product-sale-chad-delete-dialog.component';
import { ProductSaleChadRoutingModule } from './route/product-sale-chad-routing.module';

@NgModule({
  imports: [SharedModule, ProductSaleChadRoutingModule],
  declarations: [
    ProductSaleChadComponent,
    ProductSaleChadDetailComponent,
    ProductSaleChadUpdateComponent,
    ProductSaleChadDeleteDialogComponent,
  ],
})
export class ProductSaleChadModule {}
