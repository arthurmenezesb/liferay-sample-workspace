package liferay.openapi.rest.builder.internal.resource.v1_0;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.sample.module.model.SampleObject;
import com.liferay.sample.module.service.SampleService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import liferay.openapi.rest.builder.dto.v1_0.Sample;
import liferay.openapi.rest.builder.resource.v1_0.SampleResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rennan Prysthon
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sample.properties", scope = ServiceScope.PROTOTYPE,
	service = SampleResource.class
)
public class SampleResourceImpl extends BaseSampleResourceImpl {

	public Sample convertToSample(SampleObject sampleObject) {
		Sample sample = new Sample();

		sample.setId(sampleObject.getId());
		sample.setName(sampleObject.getName());

		return sample;
	}

	public SampleObject convertToSampleObject(Sample sampleObject) {
		SampleObject sample = new SampleObject();

		sample.setId(sampleObject.getId());
		sample.setName(sampleObject.getName());

		return sample;
	}

	@Override
	public Sample createSample(Sample sample) throws Exception {
		SampleObject sampleObject = _sampleService.addSample(convertToSampleObject(sample));

		return convertToSample(sampleObject);
	}

	@Override
	public void deleteSample(String sampleId) throws Exception {
		_sampleService.deleteSample(sampleId);
	}

	@Override
	public Page<Sample> getAllSamples(Integer pageNumber, Integer pageSize) throws Exception {
		List<SampleObject> sampleObjects = _sampleService.getSamples();

		Stream<SampleObject> streamSampleObjects = sampleObjects.stream();

		Stream<Sample> streamSample = streamSampleObjects.map(this::convertToSample);

		List<Sample> samples = streamSample.collect(Collectors.toList());

		return Page.of(samples);
	}

	@Override
	public Sample getSample(String sampleId) throws Exception {
		SampleObject sampleObject = _sampleService.getSample(sampleId);

		return convertToSample(sampleObject);
	}

	@Override
	public Sample putSample(String sampleId, Sample sample) throws Exception {
		SampleObject sampleObject = _sampleService.updateSample(convertToSampleObject(sample));

		return convertToSample(sampleObject);
	}

	@Reference
	private SampleService _sampleService;

}