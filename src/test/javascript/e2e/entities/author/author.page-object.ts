import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AuthorComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-author div table .btn-danger'));
  title = element.all(by.css('jhi-author div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class AuthorUpdatePage {
  pageTitle = element(by.id('jhi-author-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  pictureInput = element(by.id('file_picture'));
  infoInput = element(by.id('field_info'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  dateUpdatedInput = element(by.id('field_dateUpdated'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setPictureInput(picture) {
    await this.pictureInput.sendKeys(picture);
  }

  async getPictureInput() {
    return await this.pictureInput.getAttribute('value');
  }

  async setInfoInput(info) {
    await this.infoInput.sendKeys(info);
  }

  async getInfoInput() {
    return await this.infoInput.getAttribute('value');
  }

  async setDateCreatedInput(dateCreated) {
    await this.dateCreatedInput.sendKeys(dateCreated);
  }

  async getDateCreatedInput() {
    return await this.dateCreatedInput.getAttribute('value');
  }

  async setDateUpdatedInput(dateUpdated) {
    await this.dateUpdatedInput.sendKeys(dateUpdated);
  }

  async getDateUpdatedInput() {
    return await this.dateUpdatedInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class AuthorDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-author-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-author'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
