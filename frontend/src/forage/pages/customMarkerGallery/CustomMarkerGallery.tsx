import {CustomMarkerGalleryProps} from "./CustomMarkerGalleryProps.ts"
import {CustomMarker} from "../../types/CustomMarker.ts";
import ExpandableCard from "../../components/content/expandableCard/ExpandableCard.tsx";
import CustomMarkerEditor from "./customMarkerEditor/CustomMarkerEditor.tsx";

export default function CustomMarkerGallery(props: Readonly<CustomMarkerGalleryProps>) {

	return (
		<div className={"boxed"}>
			<h2>Custom Marker</h2>
			{props.customMarkers.length > 0 ?
				<>
					{props.customMarkers.map((customMarker: CustomMarker) => (
						<div key={customMarker.id}>
							<ExpandableCard
								customMarker={customMarker}
								fetchCustomMarkerData={props.fetchCustomMarkerData}/>
						</div>
					))}
				</>
				:
				<p>No custom marker found</p>
			}

			<h2>Add Custom Marker</h2>
			<CustomMarkerEditor
				fetchCustomMarkerData={props.fetchCustomMarkerData}
			/>
		</div>
	)
}