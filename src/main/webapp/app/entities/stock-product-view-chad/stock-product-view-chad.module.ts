import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StockProductViewChadComponent } from './list/stock-product-view-chad.component';
import { StockProductViewChadDetailComponent } from './detail/stock-product-view-chad-detail.component';
import { StockProductViewChadUpdateComponent } from './update/stock-product-view-chad-update.component';
import { StockProductViewChadDeleteDialogComponent } from './delete/stock-product-view-chad-delete-dialog.component';
import { StockProductViewChadRoutingModule } from './route/stock-product-view-chad-routing.module';

@NgModule({
  imports: [SharedModule, StockProductViewChadRoutingModule],
  declarations: [
    StockProductViewChadComponent,
    StockProductViewChadDetailComponent,
    StockProductViewChadUpdateComponent,
    StockProductViewChadDeleteDialogComponent,
  ],
})
export class StockProductViewChadModule {}
