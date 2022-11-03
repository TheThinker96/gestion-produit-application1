import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductTransactionViewChad } from '../product-transaction-view-chad.model';
import { ProductTransactionViewChadService } from '../service/product-transaction-view-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-transaction-view-chad-delete-dialog.component.html',
})
export class ProductTransactionViewChadDeleteDialogComponent {
  productTransactionView?: IProductTransactionViewChad;

  constructor(protected productTransactionViewService: ProductTransactionViewChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productTransactionViewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
