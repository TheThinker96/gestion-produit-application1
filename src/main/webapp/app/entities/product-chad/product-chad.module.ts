import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductChadComponent } from './list/product-chad.component';
import { ProductChadDetailComponent } from './detail/product-chad-detail.component';
import { ProductChadUpdateComponent } from './update/product-chad-update.component';
import { ProductChadDeleteDialogComponent } from './delete/product-chad-delete-dialog.component';
import { ProductChadRoutingModule } from './route/product-chad-routing.module';

@NgModule({
  imports: [SharedModule, ProductChadRoutingModule],
  declarations: [ProductChadComponent, ProductChadDetailComponent, ProductChadUpdateComponent, ProductChadDeleteDialogComponent],
})
export class ProductChadModule {}
