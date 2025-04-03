import { ResourceProps } from "@refinedev/core"

export const defaultResource = (name: string): ResourceProps => ({
    name,
    list: `${name}`,
    show: `${name}/show/:id`,
    create: `${name}/create`,
    edit: `${name}/edit/:id`,
    meta: {
        canDelete: true,
    },
});

export const defaultResources = (names: string[]): ResourceProps[] => {
    return names.map(name => defaultResource(name));
}

const resources: ResourceProps[] = defaultResources([
    "courses",
    "disciplines",
    "lecturers",
    "students"
]);

export default resources;