import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserSaleAccountChad } from '../user-sale-account-chad.model';
import { UserSaleAccountChadService } from '../service/user-sale-account-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-sale-account-chad-delete-dialog.component.html',
})
export class UserSaleAccountChadDeleteDialogComponent {
  userSaleAccount?: IUserSaleAccountChad;

  constructor(protected userSaleAccountService: UserSaleAccountChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userSaleAccountService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
