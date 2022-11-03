import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StockProductChadComponent } from './list/stock-product-chad.component';
import { StockProductChadDetailComponent } from './detail/stock-product-chad-detail.component';
import { StockProductChadUpdateComponent } from './update/stock-product-chad-update.component';
import { StockProductChadDeleteDialogComponent } from './delete/stock-product-chad-delete-dialog.component';
import { StockProductChadRoutingModule } from './route/stock-product-chad-routing.module';

@NgModule({
  imports: [SharedModule, StockProductChadRoutingModule],
  declarations: [
    StockProductChadComponent,
    StockProductChadDetailComponent,
    StockProductChadUpdateComponent,
    StockProductChadDeleteDialogComponent,
  ],
})
export class StockProductChadModule {}
