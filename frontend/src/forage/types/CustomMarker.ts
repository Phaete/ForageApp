import {Icon} from "./Icon.ts";

export type CustomMarker = {
	uniqueMarkerId: React.Key | null | undefined,
	position: number[],
	icon: Icon,
	popupText: string
}