import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStockProductViewChad } from '../stock-product-view-chad.model';
import { StockProductViewChadService } from '../service/stock-product-view-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './stock-product-view-chad-delete-dialog.component.html',
})
export class StockProductViewChadDeleteDialogComponent {
  stockProductView?: IStockProductViewChad;

  constructor(protected stockProductViewService: StockProductViewChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stockProductViewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
