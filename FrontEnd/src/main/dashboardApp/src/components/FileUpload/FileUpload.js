import React, {Component} from 'react'
import {DropzoneArea} from 'material-ui-dropzone'

class FileUpload extends Component{

    constructor(props){
        super(props);
    }
    handleChange(files){
        this.props.setFieldValue("files", files)
    }
    render(){
        return (
            <DropzoneArea
                showFileNames={true}
                dropzoneText={"Drag document here or click"}
                onChange={this.handleChange.bind(this)}
            />
        )
    }
}

export default FileUpload;