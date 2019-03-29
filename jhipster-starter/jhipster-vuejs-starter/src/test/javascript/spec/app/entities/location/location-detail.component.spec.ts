/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import LocationDetailComponent from '@/entities/location/location-details.vue';
import LocationClass from '@/entities/location/location-details.component';
import LocationService from '@/entities/location/location.service';

const localVue = createLocalVue();
const mockedAxios: any = axios;

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('Component Tests', () => {
  describe('Location Management Detail Component', () => {
    let wrapper: Wrapper<LocationClass>;
    let comp: LocationClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<LocationClass>(LocationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { locationService: () => new LocationService() }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveLocation(123);
        await comp.$nextTick();

        // THEN
        expect(comp.location).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
