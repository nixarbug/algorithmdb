import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ImplementationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-implementation div table .btn-danger'));
  title = element.all(by.css('jhi-implementation div h2#page-heading span')).first();

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

export class ImplementationUpdatePage {
  pageTitle = element(by.id('jhi-implementation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  codeInput = element(by.id('field_code'));
  noteInput = element(by.id('field_note'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  dateUpdatedInput = element(by.id('field_dateUpdated'));
  languageSelect = element(by.id('field_language'));
  algorithmSelect = element(by.id('field_algorithm'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return await this.noteInput.getAttribute('value');
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

  async languageSelectLastOption(timeout?: number) {
    await this.languageSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async languageSelectOption(option) {
    await this.languageSelect.sendKeys(option);
  }

  getLanguageSelect(): ElementFinder {
    return this.languageSelect;
  }

  async getLanguageSelectedOption() {
    return await this.languageSelect.element(by.css('option:checked')).getText();
  }

  async algorithmSelectLastOption(timeout?: number) {
    await this.algorithmSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async algorithmSelectOption(option) {
    await this.algorithmSelect.sendKeys(option);
  }

  getAlgorithmSelect(): ElementFinder {
    return this.algorithmSelect;
  }

  async getAlgorithmSelectedOption() {
    return await this.algorithmSelect.element(by.css('option:checked')).getText();
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

export class ImplementationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-implementation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-implementation'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
