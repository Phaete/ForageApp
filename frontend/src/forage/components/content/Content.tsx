import {ContentProps} from "./ContentProps.ts"

export default function Content(props: Readonly<ContentProps>) {
	return (
		<div className={"flex flex-col flex-grow align-center justify-center"}>
			{props.children}
		</div>
	)
}