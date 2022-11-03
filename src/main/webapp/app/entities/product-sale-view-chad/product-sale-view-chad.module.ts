import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductSaleViewChadComponent } from './list/product-sale-view-chad.component';
import { ProductSaleViewChadDetailComponent } from './detail/product-sale-view-chad-detail.component';
import { ProductSaleViewChadUpdateComponent } from './update/product-sale-view-chad-update.component';
import { ProductSaleViewChadDeleteDialogComponent } from './delete/product-sale-view-chad-delete-dialog.component';
import { ProductSaleViewChadRoutingModule } from './route/product-sale-view-chad-routing.module';

@NgModule({
  imports: [SharedModule, ProductSaleViewChadRoutingModule],
  declarations: [
    ProductSaleViewChadComponent,
    ProductSaleViewChadDetailComponent,
    ProductSaleViewChadUpdateComponent,
    ProductSaleViewChadDeleteDialogComponent,
  ],
})
export class ProductSaleViewChadModule {}
