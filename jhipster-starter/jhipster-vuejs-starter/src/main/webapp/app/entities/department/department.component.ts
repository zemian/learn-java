import { mixins } from 'vue-class-component';
import { Component, Inject, Vue } from 'vue-property-decorator';
import { IDepartment } from '@/shared/model/department.model';
import AlertService from '@/shared/alert/alert.service';

import DepartmentService from './department.service';

@Component
export default class Department extends Vue {
  @Inject('alertService')
  private alertService: () => AlertService;
  @Inject('departmentService')
  private departmentService: () => DepartmentService;
  private removeId: string = null;
  public departments: IDepartment[] = [];

  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllDepartments();
  }

  public clear(): void {
    this.retrieveAllDepartments();
  }

  public retrieveAllDepartments(): void {
    this.departmentService()
      .retrieve()
      .then(res => {
        this.departments = res.data;
      });
  }

  public prepareRemove(instance): void {
    this.removeId = instance.id;
  }

  public removeDepartment(): void {
    this.departmentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('jhipsterApp.department.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDepartments();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
