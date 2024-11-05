import {GeoPosition} from "../../../types/GeoPosition.ts";

export type FloatingMapInfoBoxProps = {
	userPosition: GeoPosition | null,
	mapCenter: {position: GeoPosition, zoom: number},
	setAddForageMapItem: (addForageMapItem: boolean) => void
}