import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AlgorithmComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-algorithm div table .btn-danger'));
  title = element.all(by.css('jhi-algorithm div h2#page-heading span')).first();

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

export class AlgorithmUpdatePage {
  pageTitle = element(by.id('jhi-algorithm-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  inputInput = element(by.id('field_input'));
  outputInput = element(by.id('field_output'));
  ideaInput = element(by.id('field_idea'));
  descriptionInput = element(by.id('field_description'));
  realLifeUseInput = element(by.id('field_realLifeUse'));
  pseudocodeInput = element(by.id('field_pseudocode'));
  flowchartInput = element(by.id('field_flowchart'));
  flowchartImageInput = element(by.id('file_flowchartImage'));
  complexityAnalysisInput = element(by.id('field_complexityAnalysis'));
  correctnessProofInput = element(by.id('field_correctnessProof'));
  averageStarsInput = element(by.id('field_averageStars'));
  totalFavsInput = element(by.id('field_totalFavs'));
  weightedRatingInput = element(by.id('field_weightedRating'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  dateUpdatedInput = element(by.id('field_dateUpdated'));
  worstCaseComplexitySelect = element(by.id('field_worstCaseComplexity'));
  averageCaseComplexitySelect = element(by.id('field_averageCaseComplexity'));
  bestCaseComplexitySelect = element(by.id('field_bestCaseComplexity'));
  authorSelect = element(by.id('field_author'));
  tagSelect = element(by.id('field_tag'));
  problemSelect = element(by.id('field_problem'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setInputInput(input) {
    await this.inputInput.sendKeys(input);
  }

  async getInputInput() {
    return await this.inputInput.getAttribute('value');
  }

  async setOutputInput(output) {
    await this.outputInput.sendKeys(output);
  }

  async getOutputInput() {
    return await this.outputInput.getAttribute('value');
  }

  async setIdeaInput(idea) {
    await this.ideaInput.sendKeys(idea);
  }

  async getIdeaInput() {
    return await this.ideaInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setRealLifeUseInput(realLifeUse) {
    await this.realLifeUseInput.sendKeys(realLifeUse);
  }

  async getRealLifeUseInput() {
    return await this.realLifeUseInput.getAttribute('value');
  }

  async setPseudocodeInput(pseudocode) {
    await this.pseudocodeInput.sendKeys(pseudocode);
  }

  async getPseudocodeInput() {
    return await this.pseudocodeInput.getAttribute('value');
  }

  async setFlowchartInput(flowchart) {
    await this.flowchartInput.sendKeys(flowchart);
  }

  async getFlowchartInput() {
    return await this.flowchartInput.getAttribute('value');
  }

  async setFlowchartImageInput(flowchartImage) {
    await this.flowchartImageInput.sendKeys(flowchartImage);
  }

  async getFlowchartImageInput() {
    return await this.flowchartImageInput.getAttribute('value');
  }

  async setComplexityAnalysisInput(complexityAnalysis) {
    await this.complexityAnalysisInput.sendKeys(complexityAnalysis);
  }

  async getComplexityAnalysisInput() {
    return await this.complexityAnalysisInput.getAttribute('value');
  }

  async setCorrectnessProofInput(correctnessProof) {
    await this.correctnessProofInput.sendKeys(correctnessProof);
  }

  async getCorrectnessProofInput() {
    return await this.correctnessProofInput.getAttribute('value');
  }

  async setAverageStarsInput(averageStars) {
    await this.averageStarsInput.sendKeys(averageStars);
  }

  async getAverageStarsInput() {
    return await this.averageStarsInput.getAttribute('value');
  }

  async setTotalFavsInput(totalFavs) {
    await this.totalFavsInput.sendKeys(totalFavs);
  }

  async getTotalFavsInput() {
    return await this.totalFavsInput.getAttribute('value');
  }

  async setWeightedRatingInput(weightedRating) {
    await this.weightedRatingInput.sendKeys(weightedRating);
  }

  async getWeightedRatingInput() {
    return await this.weightedRatingInput.getAttribute('value');
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

  async worstCaseComplexitySelectLastOption(timeout?: number) {
    await this.worstCaseComplexitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async worstCaseComplexitySelectOption(option) {
    await this.worstCaseComplexitySelect.sendKeys(option);
  }

  getWorstCaseComplexitySelect(): ElementFinder {
    return this.worstCaseComplexitySelect;
  }

  async getWorstCaseComplexitySelectedOption() {
    return await this.worstCaseComplexitySelect.element(by.css('option:checked')).getText();
  }

  async averageCaseComplexitySelectLastOption(timeout?: number) {
    await this.averageCaseComplexitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async averageCaseComplexitySelectOption(option) {
    await this.averageCaseComplexitySelect.sendKeys(option);
  }

  getAverageCaseComplexitySelect(): ElementFinder {
    return this.averageCaseComplexitySelect;
  }

  async getAverageCaseComplexitySelectedOption() {
    return await this.averageCaseComplexitySelect.element(by.css('option:checked')).getText();
  }

  async bestCaseComplexitySelectLastOption(timeout?: number) {
    await this.bestCaseComplexitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async bestCaseComplexitySelectOption(option) {
    await this.bestCaseComplexitySelect.sendKeys(option);
  }

  getBestCaseComplexitySelect(): ElementFinder {
    return this.bestCaseComplexitySelect;
  }

  async getBestCaseComplexitySelectedOption() {
    return await this.bestCaseComplexitySelect.element(by.css('option:checked')).getText();
  }

  async authorSelectLastOption(timeout?: number) {
    await this.authorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorSelectOption(option) {
    await this.authorSelect.sendKeys(option);
  }

  getAuthorSelect(): ElementFinder {
    return this.authorSelect;
  }

  async getAuthorSelectedOption() {
    return await this.authorSelect.element(by.css('option:checked')).getText();
  }

  async tagSelectLastOption(timeout?: number) {
    await this.tagSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tagSelectOption(option) {
    await this.tagSelect.sendKeys(option);
  }

  getTagSelect(): ElementFinder {
    return this.tagSelect;
  }

  async getTagSelectedOption() {
    return await this.tagSelect.element(by.css('option:checked')).getText();
  }

  async problemSelectLastOption(timeout?: number) {
    await this.problemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async problemSelectOption(option) {
    await this.problemSelect.sendKeys(option);
  }

  getProblemSelect(): ElementFinder {
    return this.problemSelect;
  }

  async getProblemSelectedOption() {
    return await this.problemSelect.element(by.css('option:checked')).getText();
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

export class AlgorithmDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-algorithm-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-algorithm'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
