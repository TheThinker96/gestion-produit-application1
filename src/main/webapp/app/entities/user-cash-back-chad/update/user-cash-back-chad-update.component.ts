import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UserCashBackChadFormService, UserCashBackChadFormGroup } from './user-cash-back-chad-form.service';
import { IUserCashBackChad } from '../user-cash-back-chad.model';
import { UserCashBackChadService } from '../service/user-cash-back-chad.service';
import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

@Component({
  selector: 'jhi-user-cash-back-chad-update',
  templateUrl: './user-cash-back-chad-update.component.html',
})
export class UserCashBackChadUpdateComponent implements OnInit {
  isSaving = false;
  userCashBack: IUserCashBackChad | null = null;
  userSaleAccountStatutValues = Object.keys(UserSaleAccountStatut);

  editForm: UserCashBackChadFormGroup = this.userCashBackFormService.createUserCashBackChadFormGroup();

  constructor(
    protected userCashBackService: UserCashBackChadService,
    protected userCashBackFormService: UserCashBackChadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userCashBack }) => {
      this.userCashBack = userCashBack;
      if (userCashBack) {
        this.updateForm(userCashBack);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userCashBack = this.userCashBackFormService.getUserCashBackChad(this.editForm);
    if (userCashBack.id !== null) {
      this.subscribeToSaveResponse(this.userCashBackService.update(userCashBack));
    } else {
      this.subscribeToSaveResponse(this.userCashBackService.create(userCashBack));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserCashBackChad>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(userCashBack: IUserCashBackChad): void {
    this.userCashBack = userCashBack;
    this.userCashBackFormService.resetForm(this.editForm, userCashBack);
  }
}
