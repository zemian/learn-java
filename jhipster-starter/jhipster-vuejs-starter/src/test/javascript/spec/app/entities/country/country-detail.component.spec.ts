/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import CountryDetailComponent from '@/entities/country/country-details.vue';
import CountryClass from '@/entities/country/country-details.component';
import CountryService from '@/entities/country/country.service';

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
  describe('Country Management Detail Component', () => {
    let wrapper: Wrapper<CountryClass>;
    let comp: CountryClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<CountryClass>(CountryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { countryService: () => new CountryService() }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveCountry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.country).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
