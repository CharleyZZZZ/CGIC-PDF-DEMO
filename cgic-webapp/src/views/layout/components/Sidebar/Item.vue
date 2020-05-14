<script>
  import { getMenuInfo } from '@/api/api'

export default {
  name: 'MenuItem',
  functional: true,
  data () {
    return {
      menus: []
    }
  },
  props: {
    meta: {
      type: Object,
      default: () => {
        return {
          title: '',
          icon: ''
        }
      }
    }
  },
//  created: function () {
//    getMenuInfo().then((res) => {
//      if (res.data.rows && res.data.rows.length > 0) {
//        this.$data.menus = res.data.rows;
//      } else {
//        this.$message.error('用户菜单获取失败！')
//      }
//    }).catch((e) => {
//      this.$message.error('用户菜单获取失败！')
//      console.log(e)
//    })
//  },
  render(h, context) {
    const { icon, title } = context.props.meta
    const vnodes = []

    if (icon) {
      vnodes.push(<svg-icon icon-class={icon}/>)
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    }
    return vnodes
  }
}
</script>
