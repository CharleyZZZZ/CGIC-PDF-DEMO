export default function (component) {
  switch (component) {
    case 'Layout':
      return require("@/views/layout/Layout").default
    default:
      return require('@/views/' + component).default
  }
}
