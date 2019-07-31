import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class SystemSettingComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-system-setting div table .btn-danger'));
  title = element.all(by.css('jhi-system-setting div h2#page-heading span')).first();

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

export class SystemSettingUpdatePage {
  pageTitle = element(by.id('jhi-system-setting-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  keySelect = element(by.id('field_key'));
  valueInput = element(by.id('field_value'));
  descriptionInput = element(by.id('field_description'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setKeySelect(key) {
    await this.keySelect.sendKeys(key);
  }

  async getKeySelect() {
    return await this.keySelect.element(by.css('option:checked')).getText();
  }

  async keySelectLastOption(timeout?: number) {
    await this.keySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setValueInput(value) {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput() {
    return await this.valueInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
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

export class SystemSettingDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-systemSetting-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-systemSetting'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
