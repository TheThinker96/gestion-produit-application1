import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStockProductChad } from '../stock-product-chad.model';
import { StockProductChadService } from '../service/stock-product-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './stock-product-chad-delete-dialog.component.html',
})
export class StockProductChadDeleteDialogComponent {
  stockProduct?: IStockProductChad;

  constructor(protected stockProductService: StockProductChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stockProductService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
