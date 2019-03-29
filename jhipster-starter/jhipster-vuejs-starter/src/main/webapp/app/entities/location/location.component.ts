import { mixins } from 'vue-class-component';
import { Component, Inject, Vue } from 'vue-property-decorator';
import { ILocation } from '@/shared/model/location.model';
import AlertService from '@/shared/alert/alert.service';

import LocationService from './location.service';

@Component
export default class Location extends Vue {
  @Inject('alertService')
  private alertService: () => AlertService;
  @Inject('locationService')
  private locationService: () => LocationService;
  private removeId: string = null;
  public locations: ILocation[] = [];

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
    this.retrieveAllLocations();
  }

  public clear(): void {
    this.retrieveAllLocations();
  }

  public retrieveAllLocations(): void {
    this.locationService()
      .retrieve()
      .then(res => {
        this.locations = res.data;
      });
  }

  public prepareRemove(instance): void {
    this.removeId = instance.id;
  }

  public removeLocation(): void {
    this.locationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('jhipsterApp.location.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllLocations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
