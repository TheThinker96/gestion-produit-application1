import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductTransactionChad } from '../product-transaction-chad.model';
import { ProductTransactionChadService } from '../service/product-transaction-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-transaction-chad-delete-dialog.component.html',
})
export class ProductTransactionChadDeleteDialogComponent {
  productTransaction?: IProductTransactionChad;

  constructor(protected productTransactionService: ProductTransactionChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productTransactionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
