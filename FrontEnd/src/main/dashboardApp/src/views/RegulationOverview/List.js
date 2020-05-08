import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import DraftsIcon from '@material-ui/icons/Drafts';

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
}));

export default function ClickableList(props) {
    const classes = useStyles();
    const { updateFileFunction, listData, listHeaderColor } = props;
    const [selectedIndex, setSelectedIndex] = React.useState(1);

    const handleListItemClick = (event, index, fileName) => {
        //console.log(event);
        //console.log(fileName);
        updateFileFunction("http://localhost:8090/files/" + fileName[0]);
        setSelectedIndex(index);
    };

    return (
        <div className={classes.root}>
            <List component="nav" aria-label="main mailbox folders">
               {listData.map((prop, key) => {
                    return (
                        <ListItem
                            button
                            selected={selectedIndex === 0}
                            onClick={(event) => handleListItemClick(event, 1, prop)}
                        >
                            <ListItemIcon>
                                <DraftsIcon />
                            </ListItemIcon>
                            <ListItemText primary={prop} />
                        </ListItem>
                    );
            })}
            </List>
        </div>
    );
}