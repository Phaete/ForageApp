import {ContentProps} from "./ContentProps.ts"

export default function Content(props: Readonly<ContentProps>) {
	return (
		<div className={"flex flex-col"}>
			{props.children}
		</div>
	)
}